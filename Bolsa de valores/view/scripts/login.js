async function login() {
  let username = document.getElementById("username").value;
  let password = document.getElementById("password").value;

  console.log(username, password);

  const response = await fetch("http://localhost:8080/login", {
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

  let key = "Authorization";
  let token = response.headers.get(key);
  window.localStorage.setItem(key, token);

  if (response.ok) {
    showToast("#okToast", "Successfully login, redirecting to logged area.");

    window.setTimeout(function () {
      window.location = "/view/index.html";
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
  if (response.status === 401)
    showToast("#errorToast", "Username or password are incorrect.");
}
