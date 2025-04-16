document.getElementById("adminLoginForm").addEventListener("submit", function (e) {
  e.preventDefault();

  const email = document.getElementById("email").value.trim();
  const password = document.getElementById("password").value;

  fetch("http://localhost:8080/api/v1/admin/login", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({ email, password })
  })
    .then(res => {
      if (!res.ok) throw new Error("Login failed");
      return res.json();
    })
    .then(data => {
      alert("Login successful!");
      localStorage.setItem("adminId", data.adminId);
      localStorage.setItem("adminName", data.name);
      window.location.href = "admin-dashboard.html";
    })
    .catch(err => {
      alert("Invalid credentials. Please try again.");
    });
});
