/* BigPicture - Lightbox-Effekt
 * Martin Haidn
 * 2014-05-22
*/


/* Closing function
------------------------------------------------------------------------------*/

window.document.onkeydown = function (e)
{
    if (!e){
        e = event;
    }
    if (e.keyCode == 27){
        lightbox_close();
    }
}

/* Event view
------------------------------------------------------------------------------*/
function lightbox_open(){
    window.scrollTo(0,0);
    document.getElementById('light').style.display='block';
    document.getElementById('fade').style.display='block';  
}

function lightbox_close(){
    document.getElementById('light').style.display='none';
    document.getElementById('fade').style.display='none';
}

function flushForm() {
    $('#title').val('');
    $('#datetimepicker').val('');
}

function setForm(title, date) {
    if (title == '') {
        $('#e-header').text('New Event');
    } else {
        $('#e-header').text(title);
    }
    $('#title').val(title);
    $('#datetimepicker').val(date);
}


/* Login
------------------------------------------------------------------------------*/

function show_login(){
    window.scrollTo(0,0);
    document.getElementById('login').style.display='block';
    document.getElementById('hide').style.display='block';  
}

function hide_login(){
    document.getElementById('login').style.display='none';
    document.getElementById('hide').style.display='none';
}

function flushLogin() {
    $('#u-name').val('');
    $('#u-passwd').val('');
}