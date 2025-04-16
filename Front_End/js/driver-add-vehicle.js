document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("vehicleForm");

  if (!form) {
    console.error("❌ Form with ID 'vehicleForm' not found.");
    return;
  }

  form.addEventListener("submit", async (e) => {
    e.preventDefault();

    const driverId = localStorage.getItem("driverId");
    if (!driverId) {
      alert("❌ Driver not logged in");
      return;
    }

    const formData = new FormData(form);
    const vehicle = {
      brand: formData.get("brand"),
      model: formData.get("model"),
      type: formData.get("type"),
      pricePerDay: parseFloat(formData.get("pricePerDay")),
      driverId: parseInt(driverId)
    };

    formData.append("vehicle", JSON.stringify(vehicle));
    formData.set("image", formData.get("vehicleImage"));

    try {
      const response = await fetch("http://localhost:8080/api/v1/vehicles/add", {
        method: "POST",
        body: formData
      });

      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message || "Failed to add vehicle");
      }

      alert("✅ Vehicle added successfully!");
      window.location.href = "driver-dashboard.html";
    } catch (err) {
      console.error("❌ Failed to add vehicle:", err);
      alert("❌ Failed to add vehicle:\n" + err.message);
    }
  });
});
