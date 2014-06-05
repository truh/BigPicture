/* FullCalendar-Setup for BigPicture
 * This file contains all functions to create, delete and
 * update events from the calendar.
 *
 * Martin Haidn
 * 2014-05-12
*/

/* This Method is setting up a new calender with our specified functionalities.
 * It also provides all the callback-interfaces which are used to interact with the calander like
 * adding new events, getting informed on click-events and more.
*/

//global vars to hold the information from the callbacks

$(document).ready(function() {

	//Loading Datetimepicker
	$('#datetimepicker').datetimepicker();
	
	var date = new Date();
	var d = date.getDate();
	var m = date.getMonth();
	var y = date.getFullYear();
	
	var calendar = $('#calendar').fullCalendar({

		//Header options for calendar-view
		header: {
			left: 'prev,next today',
			center: 'title',
			right: 'month,agendaWeek'
		},

		//Setting select-options for the calendar
		selectable: true,
		selectHelper: true,

		//functions and callback interfaces

		//Callback if an event was clicked
		eventClick: function(calEvent, jsEvent, view) {
			setForm(calEvent.title, toPickerString(calEvent.start.toString()));
			lightbox_open();
   		},

		//Callback if a day was clicked
		select: function(start, end, allDay) {

			//Flushing form for new Event
			setForm('', toPickerString(start.toString()));
			//Open Event-Input Form
			lightbox_open();
		},

		events: [
			{
				title: 'BigPicture',
				start: "2014-06-06T09:40:00.000Z"
			},

			{
				title: 'Notenschluss',
				start: "2014-06-13T17:00:00.000Z"
			},

			{
				title: 'Zeugnis',
				start: "2014-06-26T08:00:00.000Z"
			},
		]

	});
		
});

/* BigPicture - DataModel
 * Martin Haidn
 * 2014-05-28
*/

/* BigPic - Startup
------------------------------------------------------------------------------*/
window.onload = function() {
	authorize();
};

function authorize() {
	if (loginRequired()) {
		show_login();
	}
}


/* Login
------------------------------------------------------------------------------*/
function login() {
	user.name= $('#u-name').val();
	user.passwd= $('#u-passwd').val();

	if (validLoginData()) {
		//Writing the user data for this session into cookie
		document.cookie="username=" + user.name;
		document.cookie="passwd=" + user.passwd;
		hide_login();
		flushLogin();
	} else {
		alert('User and password, maybe? (;');
	}
}

function logout() {
	document.cookie= "username=;"
	document.cookie= "passwd=;"
	show_login();

}

function createUser() {
	
	if (validLoginData()) {
		var dataString= "&username=" + $('#u-name').val() + "&password=" + $('#u-passwd').val();
		getData('register', dataString);
	} else {
		alert('User and password, maybe? (;');
	}
}

//Checks if the user is logged in
function loginRequired() {
	var data= document.cookie.split(';');
	if (data == '' || data[0].substr(data[0].search('=')+1, data[0].length) == '') {
		return true;
	} else {
		user.name= data[0].substr(data[0].search('=')+1, data[0].length);
		user.passwd= data[1].substr(data[1].search('=')+1, data[1].length);
		return false;
	}

}

function validLoginData() {
	if ($('#u-name').val() == '' || $('#u-passwd').val() == '') return false;
	return true;
}
/* Session
------------------------------------------------------------------------------*/
var session;
var isLoggedIn;

function sessionExists() {
  if(session != "") return true;
  return false;
}

function setSession(session) {
  session= session;
}

function getSession() {
  return session;
}



/* User
------------------------------------------------------------------------------*/
var user= {
	'name': '',
	'passwd': ''
}

/* Event
------------------------------------------------------------------------------*/
var events;

/* Event-Structure
 * 
 * id: identifier (!required)
 * owner: creator of the event (!required)
 * title: title of the event (!required)
 * singeAppointment: true, when only one participant is allowed
 * comments: comments from the useres
 * appointments[]: times where the event can take place (!requred, at least one)
 * participants[]: users who are invited to participate
*/
/* Emptying the Textfields and set it to the default value
 *
 * Matthias EL-Far
 * 05.06.2014
*/

/* Adding a new event to the calendar
 *
 * Martin Haidn
 * 28.05.2014
*/

function addEvent() {

	//Reading eventdetails from user form
	var title= $('#title').val();
	var start= toDateString($('#datetimepicker').val());

	if (title) {
		$('#calendar').fullCalendar('renderEvent', {
			title: title,
			start: start,
			end: start,
			allDay: false
		});
		lightbox_close();
	} else {
		alert("Please selcet a title!");
	}
}

//Adds a new Event to the Calendar
function addEvent() {

	//Reading eventdetails from user form
	var title= $('#title').val();
	var start= toDateString($('#datetimepicker').val());

	if (title) {
		$('#calendar').fullCalendar('renderEvent', {
			title: title,
			start: start,
			end: start,
			allDay: false
		});
		lightbox_close();
	} else {
		alert("Please selcet a title!");
	}

}

function removeEvent(id) {

}

//Serarching for a specific Event 
function getEventByTitle(title) {
	for(title in events) {
		if (title == title) return events;
	}
	return 'No event with this title :(';
}

/* Date-Conversion
------------------------------------------------------------------------------*/

//Accapted months from a date
var acMonths= {
  Jan: '01', Feb: '02', Mar: '03', Apr: '04', Mai: '05', Jun: '06',
  Jul: '07', Aug: '08', Sep: '09', Oct: '10', Nov: '11', Dec: '12'
}

//Transform the timepicker date  into a valid timestring
function toPickerString(dateString) {
    var s= dateString.split(' ');
    return s[3] + '/' + acMonths[s[1]] + '/' + s[2] +  ' ' + s[4].substr(0, 5);
}

//Transfer the timeString to timepicker format
function toDateString(pickerTime) {
	var s= pickerTime.split('/');
	return s[0] + '-' + s[1] + '-' + s[2].substr(0, 2) + 'T' + s[2].substr(3, s[2].length) + ':00.000Z';
}


/* Participants
------------------------------------------------------------------------------*/
/* Participant-Structur
 * 
 * name: name of the user
 * appointment: choosen appointmen
 */

 function userIsAssigned() {
 	//false, if user has not choosen an appointment for the event
 }


 /* Connection
------------------------------------------------------------------------------*/
function getData(method, data="") {

	var baseString= "Basic " + btoa(user.name + ':' + user.passwd);

	jQuery.ajax({
         type: "POST",
         //Serveradresse später dynamisch!--------------------------------!
         url: "./rest?method=" + method + data,
         contentType: "application/json; charset=utf-8",
         dataType: "json",

         beforeSend: function(xhr) {
         	xhr.setRequestHeader('Authorization', baseString);
         },

         success: function (data, status, jqXHR) {
              alert('Success!');
         },

         error: function (jqXHR, status) {
              alert(errorMessage[method]);
              alert(JSON.stringify(jqrXHR));
         },

         timeout: 12000
     });
}

var errorMessage= {
	'register': 'User could not be created!',
	'getEvents': 'Unable to load Events'
}

 /* Debugging
------------------------------------------------------------------------------*/

function notImplemented() {
	alert('Not yet implemented!');
}