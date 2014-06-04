/* BigPicture - Lightbox-Effekt
 * Martin Haidn
 * 2014-05-22
*/

window.document.onkeydown = function (e)
{
    if (!e){
        e = event;
    }
    if (e.keyCode == 27){
        lightbox_close();
    }
}

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
    $('#title').val(title);
    $('#datetimepicker').val(date);
}
