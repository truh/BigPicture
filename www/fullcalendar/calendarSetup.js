/* FullCalendar-Setup for BigPicture
 * Martin Haidn
 * 2014-05-12
*/

/* This Method is setting up a new calender with our specified functionalities.
 * It also provides all the callback-interfaces which are used to interact with the calander like
 * adding new events, getting informed on click-events and more
*/
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
		select: function(start, end, allDay) {
			var title = prompt('Event Title:');
			if (title) {
				calendar.fullCalendar('renderEvent',
					{
						title: title,
						start: start,
						end: end,
						allDay: allDay
					},
					true // make the event "stick"
				);
			}
			calendar.fullCalendar('unselect');
		},
		editable: true,
		events: [
			{
				title: 'test',
				start: "2014-06-07T13:30:00.000Z"
			}
		]
	});
		
});