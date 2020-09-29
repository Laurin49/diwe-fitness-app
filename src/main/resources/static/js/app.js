$(function () {

    $("a.confirmDeletion").click(function () {
        if (!confirm("Wollen Sie wirklich löschen ?")) return false;
    });

});