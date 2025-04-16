document.addEventListener("DOMContentLoaded", function () {
  const form = document.querySelector("form");

  form.addEventListener("submit", function (e) {
    e.preventDefault();

    const email = document.querySelector("#email").value.trim();
    const password = document.querySelector("#password").value;

    fetch("http://localhost:8080/api/v1/auth/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ email, password }),
    })
      .then(res => {
        if (!res.ok) throw new Error("Login failed");
        return res.json();
      })
      .then(user => {
        localStorage.setItem("user", JSON.stringify(user));

        const role = user.role?.toUpperCase(); // Just in case

        if (role === "USER") {
          window.location.href = "dashboard.html";
        } else if (role === "DRIVER") {
          window.location.href = "driver-panel.html";
        } else if (role === "ADMIN") {
          window.location.href = "admin-dashboard.html";
        } else {
          alert("❌ Unknown role");
        }
      })
      .catch(err => {
        console.error(err);
        alert("❌ Invalid email or password");
      });
  });
});
