document.addEventListener("DOMContentLoaded", function () {
    fetch("http://localhost:8080/api/v1/vehicles/pending")
      .then(res => res.json())
      .then(vehicles => {
        const container = document.getElementById("vehicleContainer");
        container.innerHTML = "";
  
        vehicles.forEach(vehicle => {
          const card = document.createElement("div");
          card.className = "vehicle-card";
  
          const imageUrl = `http://localhost:8080/uploads/vehicles/vehicle-${vehicle.id}.jpg`;
  
          card.innerHTML = `
            <img src="${imageUrl}" alt="Vehicle Image" />
            <p><strong>Brand:</strong> ${vehicle.brand}</p>
            <p><strong>Model:</strong> ${vehicle.model}</p>
            <p><strong>Type:</strong> ${vehicle.type}</p>
            <p><strong>Price/Day:</strong> ${vehicle.pricePerDay} BDT</p>
            <p><strong>Driver:</strong> ${vehicle.driverName}</p>
            <div class="actions">
              <button class="approve" onclick="approveVehicle(${vehicle.id})">✅ Approve</button>
              <button class="reject" onclick="rejectVehicle(${vehicle.id})">❌ Reject</button>
            </div>
          `;
  
          container.appendChild(card);
        });
      });
  });
  
  function approveVehicle(id) {
    fetch(`http://localhost:8080/api/v1/vehicles/approve/${id}`, {
      method: "PUT"
    })
      .then(() => {
        alert("✅ Vehicle Approved");
        location.reload();
      })
      .catch(() => alert("❌ Failed to approve"));
  }
  
  function rejectVehicle(vehicleId, cardElement) {
    if (!confirm("Are you sure you want to reject this vehicle?")) return;
  
    fetch(`http://localhost:8080/api/v1/vehicles/reject/${vehicleId}`, {
      method: "DELETE"
    })
      .then(res => {
        if (res.ok) {
          alert("❌ Vehicle Rejected and Deleted");
          cardElement.remove(); // remove from DOM
        } else {
          alert("⚠️ Failed to reject vehicle");
        }
      });
  }
  
  