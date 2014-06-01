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
var currentStart;
var currentEnd;

$(document).ready(function() {
	
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

			alert('Youre Event: ' + calEvent.title);

   		},

		//Callback if a day was clicked
		select: function(start, end, allDay) {
			$("#title").val("");
			$("#datetimepicker").val("");
			lightbox_open();
			currentStart= start;
			currentEnd= end;
		},



		events: [
			{
				title: 'test',
				start: "2014-06-07T13:30:00.000Z"
			}
		]
	});
		
});

function getFormValues() {
	$('#title')
}

/* Adding a new event to the calendar
 *
 * Martin Haidn
 * 28.05.2014
*/

function addEvent() {

	//Reading eventdetails from user form
	var title= $('#title').val();
	var loc= $('#location').val();
	var desc= $('#description').val();

	if (title) {
		$('#calendar').fullCalendar('renderEvent', {
			title: title,
			start: currentStart,
			end: currentEnd,
			allDay: true
		});
		true // make the event "stick"
	} else {
		alert("Please choose a title!");
	}
	lightbox_close();

}

function eventTest() {
	$('#calendar').fullCalendar('renderEvent', {
		title: 'MeinEvent',
		start: "2014-05-07T13:30:00.000Z"
	});
}