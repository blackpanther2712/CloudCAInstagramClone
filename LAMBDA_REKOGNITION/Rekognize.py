import boto3
import pymysql
from datetime import datetime
from wand.image import Image
from Resize import resize_image


def request_handler(event, context):
    user_id = int(event['user_id'])
    caption = str(event['caption'])
    s3_image_url = event['posted_image']
    data_items = s3_image_url.split('/')
    photo_name = data_items[-1]
    bucket_name = data_items[-2]
    can_be_shown = True
    filtered_image_bucket_name = 'rekognition-filtered-images'

    client = boto3.client('rekognition', 'us-east-1')
    response = client.detect_moderation_labels(Image={'S3Object': {'Bucket': bucket_name, 'Name': photo_name}})

    moderation_labels = response['ModerationLabels']
    parent_labels = ['Explicit Nudity', 'Suggestive']
    explicit_content_labels = ['Nudity', 'Graphic Male Nudity', 'Graphic Female Nudity', 'Sexual Activity',
                               'Partial Nudity', 'Female Swimwear Or Underwear', 'Male Swimwear Or Underwear',
                               'Revealing Clothes']

    for label in moderation_labels:
        label_name = label['Name']
        parent_name = label['ParentName']
        confidence = float(label['Confidence'])
        if parent_name in parent_labels:
            if label_name in explicit_content_labels and confidence >= 95:
                can_be_shown = False
                break

    if can_be_shown:
        s3_connection = boto3.resource('s3')
        s3_object = s3_connection.Object(bucket_name, photo_name)
        s3_image_object = s3_object.get()

        with Image(blob=s3_image_object['Body'].read()) as image:
            resized_image = resize_image(image, 256, 256)
            resized_data = resized_image.make_blob()

        s3_resized_object = s3_connection.Object(filtered_image_bucket_name, photo_name)
        s3_resized_object.put(ACL='authenticated-read', Body=resized_data)
        url = """https://s3.amazonaws.com/{}/{}""".format(filtered_image_bucket_name, photo_name)

        try:
            print('Connecting To DB')
            host='cloudft05.cgvzp1ri0xhm.ap-southeast-1.rds.amazonaws.com'
            user='FT05DB'
            passwd='instagramclone'
            db='cloudft05'
            conn = pymysql.connect(host=host, port=3306, user=user, passwd=passwd, db=db)
            cur = conn.cursor()
            print('Connected')
            cur.execute("select max(id) from posts;")
            max_post_number = int(cur.fetchall()[0][0]) + 1
            cur.execute("""insert into posts(id,caption,like_count,created_date,posted_image,user_id) values(%s,%s,%s,%s,%s,%s)""",(max_post_number, caption, 0, datetime.now(), url, user_id))
            print('Query Execution Completed')

        except Exception, e:
            print('error')
            print(str(e))

        finally:
            cur.close()
            conn.close()
            print('Closed Resources')

        return {
            'result': 'yes',
            'response': response,
            'image_url': url
        }
    else:
        return {
            'result': 'no',
            'response': response,
            'image_url': ''
        }

