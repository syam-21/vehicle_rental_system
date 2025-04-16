document.getElementById("driverSignupForm").addEventListener("submit", async function (e) {
  e.preventDefault();

  const driver = {
    name: document.getElementById("name").value,
    email: document.getElementById("email").value,
    phone: document.getElementById("phone").value,
    password: document.getElementById("password").value,
    address: document.getElementById("address").value,
    licenseNumber: document.getElementById("licenseNumber").value,
    nidNumber: document.getElementById("nidNumber").value
  };

  const formData = new FormData();
  formData.append("driver", new Blob([JSON.stringify(driver)], { type: "application/json" }));
  formData.append("driverPhoto", document.getElementById("driverPhoto").files[0]);
  formData.append("licensePhoto", document.getElementById("licensePhoto").files[0]);
  formData.append("nidPhoto", document.getElementById("nidPhoto").files[0]);

  try {
    const res = await fetch("http://localhost:8080/api/v1/drivers/register", {
      method: "POST",
      body: formData
    });

    if (!res.ok) throw new Error("Registration failed");

    alert("✅ Driver registered successfully!");
    window.location.href = "driver-login.html";
  } catch (err) {
    console.error("❌ Failed to register driver:", err);
    alert("❌ Failed to register driver");
  }
});
