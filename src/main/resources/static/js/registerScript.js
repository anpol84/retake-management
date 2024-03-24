var department = document.getElementById("departmentDiv");
var specialization = document.getElementById("specializationDiv");
var roleInputs = document.querySelectorAll('input[name="role"]');
for (var i = 0; i < roleInputs.length; i++) {
    roleInputs[i].addEventListener('change', function() {
        var roleInput = document.querySelector('input[name="role"]:checked');
        if (roleInput) {
            var role = roleInput.value;
            if (role === "ROLE_STUDENT") {
                department.style.display = "none";
                specialization.style.display = "block";
            } else {
                department.style.display = "block";
                specialization.style.display = "none";
            }
        }
    });
}
// Скрываем список изначально
department.style.display = "none";
specialization.style.display = "none";