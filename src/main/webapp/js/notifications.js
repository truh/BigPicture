var nofies = {}

nofies.update = function() {
    if(!loginRequired()) {
        var baseString= "Basic " + btoa(user.name + ':' + user.passwd);
        jQuery.ajax({
            type: "POST",

            url: "./rest?method=getNotifications",
            contentType: "application/json; charset=utf-8",
            dataType: "json",

            beforeSend: function(xhr) {
                xhr.setRequestHeader('Authorization', baseString);
            },

            success: function (data, status, jqXHR) {
                alert("Success: " + JSON.stringify(data));
                alert(JSON.stringify(jqXHR));
            },

            error: function (jqXHR, status) {},

            timeout: 12000
        });
    }
}

$(document).ready(function() {

    $('#nofies')
    
});