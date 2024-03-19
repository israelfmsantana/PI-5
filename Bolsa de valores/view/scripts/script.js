function hideLoader() {
  document.getElementById("loading").style.display = "none";
}

getUser();

async function getUser() {
  const tasksEndpoint = "http://localhost:8080/user/user_login";
  let key = "Authorization";
  const response = await fetch(tasksEndpoint, { method: "GET", headers: new Headers
    ({ 
      Authorization: localStorage.getItem(key),
    }),
  });
  var data = await response.json();
  console.log(data);
  if (response) 
    hideLoader();

  if ( data && data.username.toLowerCase().includes("admin")) {
    getAllTasks();
  } else {
    getTasks();
  }

}






async function getTasks() {
  const tasksEndpoint = "http://localhost:8080/task/user";
  let key = "Authorization";
  const response = await fetch(tasksEndpoint, { method: "GET", headers: new Headers
    ({ 
      Authorization: localStorage.getItem(key),
    }),
  });
  var data = await response.json();
  console.log(data);
  if (response) 
    hideLoader();

  show(data);
}

function show(tasks) {
  let tab = `<thead>
            <th scope="col">#</th>
            <th scope="col">Description</th>
        </thead>`;

  for (let task of tasks) {
    tab += `
            <tr>
                <td scope="row">${task.id}</td>
                <td>${task.description}</td>
            </tr>
        `;
  }

  document.getElementById("tasks").innerHTML = tab;
}






document.addEventListener("DOMContentLoaded", function (event) {
  if (!localStorage.getItem("Authorization"))
    window.location = "/view/login.html";
});






async function getAllTasks() {
  const tasksEndpoint = "http://localhost:8080/task/user_admin";
  let key = "Authorization";
  const response = await fetch(tasksEndpoint, { method: "GET", headers: new Headers
    ({ 
      Authorization: localStorage.getItem(key),
    }),
  });
  var data = await response.json();
  console.log(data);
  if (response) 
    hideLoader();

    showAllTasks(data);
}

function showAllTasks(tasksAllUser) {
  let tab = `<thead>
            <th scope="col">#</th>
            <th scope="col">Description</th>
            <th scope="col">Username</th>
        </thead>`;

  for (let task of tasksAllUser) {
    tab += `
            <tr>
                <td scope="row">${task.id}</td>
                <td>${task.description}</td>
                <td>${task.user.username}</td>
            </tr>
        `;
  }

  document.getElementById("tasks").innerHTML = tab;
}