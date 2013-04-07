$(document).ready(function() {

    var ENTER_KEYCODE = 13;
    var ESC_KEYCODE = 27;
    var USER_ENDPOINT = '/user';


    var updateDisplayedUsernameWithData = function(data, textStatus, jqXHR) {
        if(data.name) {
            $('#usernamePlaceholder').text(data.name);
        }
    }

    var updateUsernameOnServer = function() {
        var newName = $('#userNameInput').val();

        if(!newName || newName == "" || /^\s*$/.test(newName)) {
            $('#emptyUsernameAlert').fadeIn(300);
        } else {
            var jsonData = { name: newName };

            $.ajax({
                type: 'POST',
                url: USER_ENDPOINT,
                data: JSON.stringify(jsonData),
                dataType: 'json',
                contentType: "application/json; charset=utf-8",
                success: updateDisplayedUsernameWithData
            });

            $('#changeUsernameModal').modal('hide');
        }
    }

    var updateDisplayedUsername = function() {
        $.ajax({
            type: 'GET',
            url: USER_ENDPOINT,
            dataType: 'json',
            success: updateDisplayedUsernameWithData
        });
    }

    $('#saveUsernameChange').click(updateUsernameOnServer);

    $('#userNameInput').keypress(function(event) {
        if (event.which == ENTER_KEYCODE) {
            event.preventDefault();
            updateUsernameOnServer();
        }
    });

    $('#changeUsernameModal').on('hidden', function () {
        $('#emptyUsernameAlert').hide(0);
    })


    var addNewRoom = function() {
        $.ajax({
            type: 'POST',
            url: '/room/new',
            success: refreshView
        });
    }

    var refreshView = function(data, textStatus, jqXHR) {
        window.location.assign('/activeRooms');
    }

    $('#addNewRoom').click(addNewRoom);
});
