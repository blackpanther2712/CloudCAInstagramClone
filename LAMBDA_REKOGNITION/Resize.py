def resize_image(image, resize_width, resize_height):
    if resize_width == image.width and resize_height == image.height:
        return image

    original_ratio = image.width / float(image.height)
    resize_ratio = resize_width / float(resize_height)

    if original_ratio > resize_ratio:
        resize_height = int(round(resize_width / original_ratio))
    else:
        resize_width = int(round(resize_height * original_ratio))

    # mitchell & lanczos2 are filter_name types
    if ((image.width - resize_width) + (image.height - resize_height)) < 0:
        filter_name = 'mitchell'
    else:
        filter_name = 'lanczos2'

    image.resize(width=resize_width, height=resize_height, filter=filter_name, blur=1)

    return image