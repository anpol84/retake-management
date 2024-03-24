
var institutesSelect = document.getElementById("institutes");
institutesSelect.addEventListener("change", function() {
    var selectedOptions = institutesSelect.selectedOptions;
    selectedInstitutes = [];
    for (var i = 0; i < selectedOptions.length; i++) {
        selectedInstitutes.push(selectedOptions[i].value);
    }
    updateSpecializations();
});
function updateSpecializations() {
    var specializationsSelect = document.getElementById("specializations");
    var options = specializationsSelect.options;
    for (var i = 0; i < options.length; i++) {
        var option = options[i];
        if (selectedInstitutes.includes(option.getAttribute("data-institute"))) {
            option.style.display = "block";
        } else {
            option.style.display = "none";
        }
    }
}