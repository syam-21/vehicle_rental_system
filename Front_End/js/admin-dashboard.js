document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("adminName").textContent =
      "Welcome, " + (localStorage.getItem("adminName") || "Admin");
  
    // 🟩 Load all registered drivers with trip stats
    fetch("http://localhost:8080/api/v1/drivers/all")
      .then(res => res.json())
      .then(drivers => {
        const container = document.getElementById("driverContainer");
        container.innerHTML = "";
  
        drivers.forEach(driver => {
          const card = document.createElement("div");
          card.className = "driver-card";
  
          card.innerHTML = `
            <img src="http://localhost:8080/uploads/drivers/${driver.driverPhoto}" alt="Driver Photo" />
            <p><strong>Name:</strong> ${driver.name}</p>
            <p><strong>Email:</strong> ${driver.email}</p>
            <p><strong>Phone:</strong> ${driver.phone}</p>
            <p><strong>Gender:</strong> ${driver.gender}</p>
            <p><strong>License No:</strong> ${driver.licenseNumber}</p>
            <p><strong>Trips:</strong> Loading...</p>
            <p><strong>Total Earned:</strong> Loading...</p>
          `;
  
          container.appendChild(card);
  
          // ✅ Load trip summary
          fetch(`http://localhost:8080/api/v1/bookings/driver/${driver.id}/summary`)
            .then(res => res.json())
            .then(data => {
              card.querySelector("p:nth-of-type(7)").textContent = `Trips: ${data.tripCount}`;
              card.querySelector("p:nth-of-type(8)").textContent = `Total Earned: ${data.totalEarnings} BDT`;
            })
            .catch(err => {
              card.querySelector("p:nth-of-type(7)").textContent = `Trips: 0`;
              card.querySelector("p:nth-of-type(8)").textContent = `Total Earned: 0 BDT`;
            });
        });
      });
  
    // 🟨 Load pending vehicles for approval
    fetch("http://localhost:8080/api/v1/vehicles/pending")
      .then(res => res.json())
      .then(vehicles => {
        const vehicleContainer = document.getElementById("vehicleContainer");
        vehicleContainer.innerHTML = "";
  
        vehicles.forEach(vehicle => {
          const card = document.createElement("div");
          card.className = "driver-card";
  
          card.innerHTML = `
            <img src="http://localhost:8080/uploads/vehicles/${vehicle.imagePath}" alt="Vehicle Image" />
            <p><strong>Brand:</strong> ${vehicle.brand}</p>
            <p><strong>Model:</strong> ${vehicle.model}</p>
            <p><strong>Type:</strong> ${vehicle.type}</p>
            <p><strong>Price/Day:</strong> ${vehicle.pricePerDay} BDT</p>
            <p><strong>Driver:</strong> ${vehicle.driver.name}</p>
            <button class="approve-btn">✅ Approve</button>
            <button class="reject-btn">❌ Reject</button>
          `;
  
          vehicleContainer.appendChild(card);
  
          // ✅ Approve
          card.querySelector(".approve-btn").addEventListener("click", () => {
            fetch(`http://localhost:8080/api/v1/vehicles/approve/${vehicle.id}`, {
              method: "PUT"
            })
              .then(() => {
                alert("✅ Vehicle approved");
                card.remove();
              })
              .catch(() => alert("Approval failed"));
          });
  
          // ❌ Reject
          card.querySelector(".reject-btn").addEventListener("click", () => {
            fetch(`http://localhost:8080/api/v1/vehicles/reject/${vehicle.id}`, {
              method: "DELETE"
            })
              .then(() => {
                alert("❌ Vehicle rejected");
                card.remove();
              })
              .catch(() => alert("Rejection failed"));
          });
        });
      });
  });
  