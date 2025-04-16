document.getElementById("registerForm").addEventListener("submit", function (e) {
  e.preventDefault();

  const name = document.getElementById("name").value.trim();
  const email = document.getElementById("email").value.trim();
  const password = document.getElementById("password").value.trim();

  fetch("http://localhost:8080/api/v1/auth/register", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({ name, email, password })
  })
    .then(res => {
      if (!res.ok) throw new Error("Registration failed");
      return res.text();
    })
    .then(data => {
      alert("Registration successful ✅");
      window.location.href = "login.html";
    })
    .catch(err => {
      console.error(err);
      alert("Registration failed ❌: " + err.message);
    });
});
