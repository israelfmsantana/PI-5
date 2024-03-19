async function signup() {
  let username = document.getElementById("username").value;
  let password = document.getElementById("password").value;

  console.log(username, password);

  const response = await fetch("http://localhost:8080/user", {
    method: "POST",
    headers: new Headers({
      "Content-Type": "application/json; charset=utf8",
      Accept: "application/json",
    }),
    body: JSON.stringify({
      username: username,
      password: password,
    }),
  });

  if (response.ok) {
    showToast("#okToast", "Successfully registered user.");

    window.setTimeout(function () {
      window.location = "/view/login.html";
    }, 2000);
  } else {
    handleSignupError(response);
  }
}

function showToast(id, message) {
  var toastEl = document.querySelector(id);
  var toast = new bootstrap.Toast(toastEl);
  var toastBody = toastEl.querySelector(".toast-body");
  
  // Define a mensagem no corpo do toast
  toastBody.textContent = message;

  // Exibe o toast
  toast.show();
}


async function handleSignupError(response) {
    if (response.status === 422)
      showToast("#errorToast", "Password needs at least 8 characters.");
    else if (response.status === 409)
      showToast("#errorToast", "This username already exists.");
    else 
      showToast("#errorToast", "Error when creating a new user.");
}
