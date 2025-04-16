document.addEventListener("DOMContentLoaded", function () {
  const user = JSON.parse(localStorage.getItem("user"));
  if (!user) {
    alert("Please login first");
    window.location.href = "index.html";
    return;
  }

  fetch("http://localhost:8080/api/v1/vehicles/user/approved")
    .then(res => res.json())
    .then(vehicles => {
      const container = document.getElementById("vehicleContainer");
      container.innerHTML = "";

      if (vehicles.length === 0) {
        container.innerHTML = "<p>No vehicles available now.</p>";
        return;
      }

      vehicles.forEach(vehicle => {

        const card = document.createElement("div");
        card.className = "vehicle-card";
      
        const imageUrl = `http://localhost:8080/uploads/vehicles/${vehicle.imagePath}`;
      
        card.innerHTML = `
          <img src="${imageUrl}" alt="Vehicle Image" class="vehicle-image" />
          <h3>${vehicle.brand} ${vehicle.model}</h3>
          <p>Type: ${vehicle.type}</p>
          <p>Driver: ${vehicle.driver?.name || "N/A"}</p>
          <p>Availability: ${vehicle.driver?.available ? "✅ Available" : "❌ Unavailable"}</p>
          <button class="book-btn" onclick="startBooking(${vehicle.id})">Book Now</button>
        `;
      
        document.getElementById("vehicleContainer").appendChild(card);
      });
      
      // 🔗 Add event listeners after cards are added
      document.querySelectorAll(".book-btn").forEach(btn => {
        btn.addEventListener("click", function () {
          const vehicleId = this.getAttribute("data-id");
          localStorage.setItem("selectedVehicleId", vehicleId);
          window.location.href = "book-vehicle.html";
        });
      });
    })
    .catch(err => {
      console.error("Failed to load vehicles:", err);
    });
    function startBooking(vehicleId) {
      localStorage.setItem("selectedVehicleId", vehicleId);
      window.location.href = "book-vehicle.html";
    }
    
});
