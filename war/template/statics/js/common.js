/**
 * 获取url后面的参数
 * @param name
 * @returns
 */
function getParamString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null)
        return unescape(r[2]);
    return null;
}

function baseAjax(url, param, callback, async) {
    $.ajax({
        type : "post",
        url : url,
        async : async == undefined ? true : false,
        data : param,
        dataType : "json",
        success : function(msg) {
            callback(msg);
        },
        error : function() {
            callback();
        }
    });
}