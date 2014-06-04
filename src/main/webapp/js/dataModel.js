/* BigPicture - DataModel
 * Martin Haidn
 * 2014-05-28
*/

var dataModel= {
/* BigPic - Startup
------------------------------------------------------------------------------*/


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

//Function to get the session from the server, not tested yet!
function buildSession() {
  jQuery.ajax({
         type: "POST",
         //Serveradresse später dynamisch!--------------------------------!
         url: "./rest?method=getSession",
         contentType: "application/json; charset=utf-8",
         dataType: "json",
         success: function (data, status, jqXHR) {
              alert(data);
         },

         error: function (jqXHR, status) {
              alert('Unable to load Events!\nStatus: ' + status);
              alert(JSON.stringify(jqXHR));
         },

         timeout: 12000
     });
}

/* User
------------------------------------------------------------------------------*/
var user;

//Sets the user of this session
function setUser(user) {
	user= user;
}

//Return the user of this session
function getUser() {
	return user;
}

function getUsername(){
	var username=$('#username-fl').val();
	return username;
}
function getPassword(){
	var password=$("#password-fl").val();
	return password;
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

function loadEvents() {
	jQuery.ajax({
         type: "POST",
         //Serveradresse später dynamisch!--------------------------------!
         url: "./rest?method=getEvents",
         contentType: "application/json; charset=utf-8",
         dataType: "json",
         success: function (data, status, jqXHR) {
              alert(JSON.stringify(data);
         },

         error: function (jqXHR, status) {
              alert('Unable to load Events!\nStatus: ' + status);
              alert(JSON.stringify(jqXHR));
         },

         timeout: 12000
     });
}

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
  Jan: 01, Feb: 02, Mar: 03, Apr: 04, Mai: 05, Jun: 06,
  Jul: 07, Aug: 08, Sep: 09, Oct: 10, Nov: 11, Dec: 12
}

//Transform the timepicker date  into a valid timestring

function toPickerString(dateString) {
    var s= dateString.split(' ');
    return s[3] + '/' + s[1] + '/' + s[2] +  ' ' + s[4].substr(0, 5);
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
}