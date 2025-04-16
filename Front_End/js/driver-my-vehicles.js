document.addEventListener("DOMContentLoaded", async () => {
    const driverId = localStorage.getItem("driverId");
  
    if (!driverId) {
      alert("Driver not logged in");
      window.location.href = "driver-login.html";
      return;
    }
  
    try {
      const res = await fetch(`http://localhost:8080/api/v1/vehicles/driver/${driverId}`);
      const vehicles = await res.json();
  
      const container = document.getElementById("vehicleList");
      container.innerHTML = "";
  
      if (vehicles.length === 0) {
        container.innerHTML = "<p>No vehicles found.</p>";
        return;
      }
  
      vehicles.forEach(vehicle => {
        const card = document.createElement("div");
        card.className = "vehicle-card";
        card.innerHTML = `
          <img src="http://localhost:8080/uploads/vehicles/vehicle-${vehicle.id}.jpg" alt="Vehicle Image">
          <h3>${vehicle.brand} ${vehicle.model}</h3>
          <p>Type: ${vehicle.type}</p>
          <p>Price per Day: ৳${vehicle.pricePerDay}</p>
          <p>Status: <strong>${vehicle.approved ? "✅ Approved" : "⏳ Pending"}</strong></p>
        `;
        container.appendChild(card);
      });
  
    } catch (err) {
      console.error("Failed to load vehicles", err);
      alert("❌ Could not fetch vehicle list");
    }
  });
  