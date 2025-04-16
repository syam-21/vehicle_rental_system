document.addEventListener("DOMContentLoaded", async () => {
    const list = document.getElementById("vehicleList");
  
    try {
      const res = await fetch("http://localhost:8080/api/v1/vehicles/unapproved");
      const vehicles = await res.json();
  
      if (vehicles.length === 0) {
        list.innerHTML = "<p>No pending vehicles</p>";
        return;
      }
  
      list.innerHTML = "";
      vehicles.forEach(vehicle => {
        const card = document.createElement("div");
        card.className = "vehicle-card";
  
        card.innerHTML = `
          <h3>${vehicle.brand} ${vehicle.model}</h3>
          <p>Type: ${vehicle.type}</p>
          <p>Price: BDT ${vehicle.pricePerDay}</p>
          <p>Driver ID: ${vehicle.driverId}</p>
          <button onclick="approveVehicle(${vehicle.id})">✅ Approve</button>
          <button onclick="rejectVehicle(${vehicle.id})">❌ Reject</button>
        `;
  
        list.appendChild(card);
      });
    } catch (err) {
      console.error("Failed to load vehicles", err);
      list.innerHTML = "<p>❌ Error loading vehicle list</p>";
    }
  });
  
  async function approveVehicle(id) {
    const res = await fetch(`http://localhost:8080/api/v1/vehicles/${id}/approve`, {
      method: "PUT"
    });
    if (res.ok) {
      alert("✅ Vehicle approved");
      location.reload();
    } else {
      alert("❌ Failed to approve");
    }
  }
  
  async function rejectVehicle(id) {
    const res = await fetch(`http://localhost:8080/api/v1/vehicles/${id}/reject`, {
      method: "PUT"
    });
    if (res.ok) {
      alert("❌ Vehicle rejected");
      location.reload();
    } else {
      alert("❌ Failed to reject");
    }
  }
  