$(document).ready(function () {
    if($("[id$='F']")) {
        var eselect = $("[id$='F']");
        var select = (eselect);
        $(select).removeClass('btn-info');
        $(select).addClass('btn-danger');

        var value = $(eselect).find("span");
        $(value).removeClass('glyphicon glyphicon-plus-sign');
        $(value).addClass('glyphicon glyphicon-minus-sign');
        var obj = $(value);
        obj.data('followed', true);
        obj.html('Unfollow');
    }
});

$(document).ready(function () {
    if($("[id$='L']")) {
        var eselect = $("[id$='L']");
        var select = (eselect);
        $(select).removeClass('btn-success');
        $(select).addClass('btn-danger');
        var value = $(eselect).find("span");
        $(value).removeClass('glyphicon glyphicon-thumbs-up');
        $(value).addClass('glyphicon glyphicon-thumbs-down');
        var obj = $(value);
        obj.data('liked', true);
        obj.html('unlike');
    }
});


var follow = function (id)
{

    var u = id.slice(0,-1);

    var select = ("#"+id+".btn");
    $(select).removeClass('btn-danger');
    $(select).addClass('btn-info');

    var value = $("#"+id).find("span");

    $(value).removeClass('glyphicon glyphicon-minus-sign');
    $(value).addClass('glyphicon glyphicon-plus-sign');
    $(value).css("background-color", "#46b8da");
    var obj = $(value);
    if( obj.data('followed') ){
        obj.data('followed', false);
        obj.html('Follow');
        var url = '/follow/';
        url=url+u;
        $("#resultsBlock1").load(url);
    }

    else{
        $(select).removeClass('btn-info');
        $(select).addClass('btn-danger');
        $(value).removeClass('glyphicon glyphicon-plus-sign');
        $(value).addClass('glyphicon glyphicon-minus-sign');
        $(value).css("background-color", "#dc3545");
        obj.data('followed', true);
        obj.html('Unfollow');
        var url = '/follow/';
        url=url+u+'d';
        $("#resultsBlock1").load(url);

    }
}

var like = function (id)
{
    var u = id.slice(0,-1);
    var select = ("#"+id+".btn");
    $(select).removeClass('btn-danger');
    $(select).addClass('btn-success');
    var value = $("#"+id).find("span");
    $(value).removeClass('glyphicon glyphicon-thumbs-down');
    $(value).addClass('glyphicon glyphicon-thumbs-up');
    $(value).css("background-color", "#218838");
    var obj = $(value);
    if( obj.data('liked') ){
        obj.data('liked', false);
        obj.html('Like');
        var url = '/likes/';
        url=url+u;
        $("#resultsBlock").load(url);
    }
    else{
        $(select).removeClass('btn-success');
        $(select).addClass('btn-danger');
        $(value).removeClass('glyphicon glyphicon-thumbs-up');
        $(value).addClass('glyphicon glyphicon-thumbs-down');
        $(value).css("background-color", "#dc3545");
        obj.data('liked', true);
        obj.html('Unlike');
        var url = '/likes/';
        url=url+u+'d';
        $("#resultsBlock").load(url);
    }
}
