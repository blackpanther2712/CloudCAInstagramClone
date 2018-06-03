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
var like = function (id)
{
    var u = id.slice(0,-1);
    var select = ("#"+id+".btn");
    $(select).removeClass('btn-danger');
    $(select).addClass('btn-success');
    var value = $("#"+id).find("span");
    $(value).removeClass('glyphicon glyphicon-thumbs-down');
    $(value).addClass('glyphicon glyphicon-thumbs-up');
    $(value).css("background-color", "#28a745");
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
