var initWebSocket = function(websocketConnectionString) {
    if(typeof(WebSocket) != 'function' ) {
        $('#browserSupportFailureAlert').fadeIn(1000);
    } else {
        var connection = new WebSocket(websocketConnectionString);

        // When the connection is open, setup & enable the send button.
        connection.onopen = function () {
            $('#pingButton').click(function(){
                connection.send('ping');
                $('#webSocketConsole').append('Sent: ping\n');
            });
            $('#pingButton').prop("disabled", false);
        };

        // Log errors
        connection.onerror = function (error) {
            $('#webSocketConsole').append('WebSocket Error: ' + error + '\n');
        };

        // Log messages from the server
        connection.onmessage = function (e) {
            $('#webSocketConsole').append('Received: ' + e.data + '\n');
        };
    }
}
