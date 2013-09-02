var initVideo = function() {
    var lmsObject;

    hasGetUserMedia = function() {
        return !!(navigator.getUserMedia || navigator.webkitGetUserMedia ||
            navigator.mozGetUserMedia || navigator.msGetUserMedia);
    }

    myGetUserMedia = function() {
        return (navigator.getUserMedia || navigator.webkitGetUserMedia ||
            navigator.mozGetUserMedia || navigator.msGetUserMedia);
    }

    onGotStream = function(stream) {
        var createObjectURL = (window.URL || window.webkitURL || {}).createObjectURL || function(){};
        var url = createObjectURL(stream);
        video.src = url;
        lmsObject = stream;
        $('#stopButton').prop('disabled', false);
        $('#startButton').prop('disabled', true);
    }

    onFailedStream = function(stream) {
    }

    startTest = function() {
        navigator.getUserMedia || (navigator.getUserMedia = navigator.mozGetUserMedia || navigator.webkitGetUserMedia || navigator.msGetUserMedia);
        navigator.getUserMedia({video:true}, onGotStream, onFailedStream);
    }

    stopTest = function() {
        video.pause();
        lmsObject.stop();
        $('#stopButton').prop('disabled', true);
        $('#startButton').prop('disabled', false);
    }

    $('#stopButton').click(stopTest);
    $('#startButton').click(startTest);
};
