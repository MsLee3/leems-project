document.addEventListener('DOMContentLoaded', function() {
    let logoutLinks = document.querySelectorAll('a.logout-link');

    logoutLinks.forEach(function(logoutLink) {
        logoutLink.addEventListener('click', function(event) {
            event.preventDefault();
            let confirmation = confirm('ログアウトしてもよろしいですか？');
            if (confirmation) {
                window.location.href = logoutLink.href;
            }
        });
    });
});