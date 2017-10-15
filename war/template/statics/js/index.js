
$(function(){
    $("#requestAdmin").click(function(){
        $.ajax({
            type : "POST",
            url : "/user/findUserByUserId.json",
            async : false,
            success : function(msg) {
                //var users = msg.userList;
                //for(var i=0; i<users.length; i++){
                    //var user = users[i];
                    //alert(user.userName);
                //}
            },
            error : function() {

            }
        });
    });
});