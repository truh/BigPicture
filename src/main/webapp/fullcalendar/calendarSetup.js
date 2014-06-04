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
				title: 'test',
				start: "2014-06-07T13:30:00.000Z"
			}
		]

	});
		
});


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