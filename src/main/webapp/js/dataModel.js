/* BigPicture - DataModel
 * Martin Haidn
 * 2014-05-28
*/


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
 * owner: creator of the event (!required)
 * title: title of the event (!required)
 * location: location where the event takes place
 * description: short event-instruction
 * appointments[]: times where the event can take place (!requred, at least one)
 * participants[]: users who are invited to participate
*/

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