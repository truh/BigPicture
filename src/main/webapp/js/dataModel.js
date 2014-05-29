/* BigPicture - DataModel
 * Martin Haidn
 * 2014-05-28
*/

/* Session
------------------------------------------------------------------------------*/


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
              alert(data);
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