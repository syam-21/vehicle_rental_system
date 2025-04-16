document.getElementById("driverLoginForm").addEventListener("submit", async function (e) {
  e.preventDefault();

  const email = document.getElementById("email").value;
  const password = document.getElementById("password").value;

  try {
    const res = await fetch("http://localhost:8080/api/v1/drivers/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({ email, password })
    });

    if (!res.ok) throw new Error("Login failed");

    const driver = await res.json();
    localStorage.setItem("driverId", driver.id); // ✅ Save driver ID
    alert("✅ Login successful!");
    window.location.href = "driver-dashboard.html";
  } catch (err) {
    console.error("❌ Login failed:", err);
    alert("❌ Invalid email or password");
  }
});
