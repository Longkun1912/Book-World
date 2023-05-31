$(document).ready(function(){
    // Initialize the datepicker
    $('.input-daterange').datepicker({
        format: 'dd-mm-yyyy',
        autoclose: true,
        calendarWeeks : true,
        clearBtn: true,
        disableTouchKeyboard: true
    });
});

$('#datepicker-submit').click(function(event) {
    event.preventDefault();

    // Get the selected start and end dates
    var startDate = $('#start').val();
    var endDate = $('#end').val();

    // Convert the dates to the format expected by the server
    var formattedStartDate = moment(startDate, 'DD-MM-YYYY').format('YYYY-MM-DD');
    var formattedEndDate = moment(endDate, 'DD-MM-YYYY').format('YYYY-MM-DD');

    // Construct the URL with the formatted dates as query parameters
    var url = '/admin/book-index?startDate=' + formattedStartDate + '&endDate=' + formattedEndDate;

    // Redirect to the URL
    window.location.href = url;
});