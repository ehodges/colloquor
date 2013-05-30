describe("Test the UserDisplay object.", function() {
    it("Test update the Username displayed.", function() {
        initColloquor();

        var sampleElement = $('<span id="usernamePlaceholder">John Cleese</span>');
        var userDisplay = new UserDisplay(sampleElement);

        userDisplay.update('Eric Idle');

        expect(sampleElement.text()).toBe('Eric Idle');
    });
});

describe("Test the ChangeUsernameModal object.", function() {
    it("Test modal show/hide.", function () {
        initColloquor();

        var sampleElement = $('<div id="changeUsernameModal">John Cleese</div>');
        var changeUsernameModal = new ChangeUsernameModal(sampleElement);
        expect(sampleElement.is(':hidden')).toBe(true);
        changeUsernameModal.show();
        expect(sampleElement.is(':hidden')).toBe(false);
        changeUsernameModal.hide();
        expect(sampleElement.is(':hidden')).toBe(true);
    })
})