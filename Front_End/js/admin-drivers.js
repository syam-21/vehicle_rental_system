document.addEventListener("DOMContentLoaded", function () {
    fetch("http://localhost:8080/api/v1/drivers/all")
      .then(res => res.json())
      .then(drivers => {
        const container = document.getElementById("driverContainer");
        container.innerHTML = "";
  
        drivers.forEach(driver => {
          const card = document.createElement("div");
          card.className = "driver-card";
  
          // ✅ Build image using driver ID
          const photoUrl = `http://localhost:8080/uploads/drivers/driver-${driver.id}.jpg`;
  
          card.innerHTML = `
            <img src="${photoUrl}" alt="Driver Photo" onerror="this.src='assets/img/placeholder-user.png'" />
            <p><strong>Name:</strong> ${driver.name}</p>
            <p><strong>Email:</strong> ${driver.email}</p>
            <p><strong>Phone:</strong> ${driver.phone}</p>
            <p><strong>Gender:</strong> ${driver.gender || 'Not set'}</p>
            <p><strong>License No:</strong> ${driver.licenseNumber}</p>
            <div class="driver-actions">
              <button class="details-btn" onclick="viewDetails(${driver.id})">🔍 More Details</button>
              <button class="delete-btn" onclick="deleteDriver(${driver.id})">🗑️ Delete</button>
            </div>
          `;
  
          container.appendChild(card);
        });
      });
  });
  
  function deleteDriver(driverId) {
    if (!confirm("Are you sure you want to delete this driver?")) return;
  
    fetch(`http://localhost:8080/api/v1/drivers/${driverId}`, {
      method: "DELETE"
    })
      .then(res => {
        if (res.ok) {
          alert("Driver deleted successfully!");
          location.reload();
        } else {
          alert("Failed to delete driver.");
        }
      });
  }
  
  function viewDetails(driverId) {
    localStorage.setItem("selectedDriverId", driverId);
    window.location.href = "admin-driver-details.html";
  }
  