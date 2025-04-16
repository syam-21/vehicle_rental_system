document.addEventListener("DOMContentLoaded", function () {
    const driverId = localStorage.getItem("selectedDriverId");
    if (!driverId) {
      document.getElementById("driverDetails").textContent = "No driver selected.";
      return;
    }
  
    fetch(`http://localhost:8080/api/v1/drivers/${driverId}`)
      .then(res => res.json())
      .then(driver => {
        const div = document.getElementById("driverDetails");
        div.innerHTML = `
          <img src="http://localhost:8080/uploads/drivers/driver-${driver.id}.jpg" alt="Driver Photo" />
          <p><strong>Name:</strong> ${driver.name}</p>
          <p><strong>Email:</strong> ${driver.email}</p>
          <p><strong>Phone:</strong> ${driver.phone}</p>
          <p><strong>Gender:</strong> ${driver.gender || 'Not set'}</p>
          <p><strong>Address:</strong> ${driver.address}</p>
          <p><strong>License Number:</strong> ${driver.licenseNumber}</p>
          <img src="http://localhost:8080/uploads/drivers/license-${driver.id}.jpg" alt="License Photo" />
          <p><strong>NID Number:</strong> ${driver.nidNumber}</p>
          <img src="http://localhost:8080/uploads/drivers/nid-${driver.id}.jpg" alt="NID Photo" />
          <p><strong>Trips Completed:</strong> ${driver.tripCount}</p>
          <p><strong>Rating:</strong> ${driver.rating}</p>
        `;
      })
      .catch(() => {
        document.getElementById("driverDetails").textContent = "Driver not found or error occurred.";
      });
  });
  