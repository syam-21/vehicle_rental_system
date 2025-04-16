document.addEventListener("DOMContentLoaded", async () => {
    const driverId = localStorage.getItem("driverId");
  
    if (!driverId) {
      alert("Driver not logged in");
      window.location.href = "driver-login.html";
      return;
    }
  
    try {
      const res = await fetch(`http://localhost:8080/api/v1/drivers/${driverId}`);
      const data = await res.json();
  
      document.getElementById("driverName").textContent = data.name;
      document.getElementById("driverEmail").textContent = data.email;
      document.getElementById("driverPhone").textContent = data.phone;
      document.getElementById("driverAddress").textContent = data.address;
      document.getElementById("tripCount").textContent = data.tripCount;
      document.getElementById("rating").textContent = data.rating;
  
      document.getElementById("driverPhoto").src = `http://localhost:8080/uploads/drivers/driver-${data.id}.jpg`;
      document.getElementById("availabilityStatus").textContent = data.available ? "Available ✅" : "Unavailable ❌";
    } catch (err) {
      console.error("Failed to load driver info", err);
      alert("❌ Could not load dashboard");
    }
  });
  
  // ✅ Logout
  document.getElementById("logoutBtn").addEventListener("click", () => {
    localStorage.removeItem("driverId");
    window.location.href = "driver-login.html";
  });
  
  // ✅ Toggle Availability
  document.getElementById("updateStatusBtn").addEventListener("click", async () => {
    const driverId = localStorage.getItem("driverId");
    if (!driverId) return alert("Not logged in");
  
    try {
      const res = await fetch(`http://localhost:8080/api/v1/drivers/${driverId}/availability`, {
        method: "PUT",
      });
      const available = await res.json();
      alert(`Availability updated: ${available ? "Available" : "Unavailable"}`);
      location.reload();
    } catch (err) {
      console.error("Failed to update availability", err);
      alert("❌ Could not update availability");
    }
  });
  
  // ✅ View My Bookings
  document.getElementById("viewBookingsBtn").addEventListener("click", async () => {
    const driverId = localStorage.getItem("driverId");
    const section = document.createElement("section");
    section.className = "driver-bookings";
  
    try {
      const res = await fetch(`http://localhost:8080/api/v1/bookings/driver/${driverId}`);
      const bookings = await res.json();
  
      if (!bookings.length) {
        section.innerHTML = "<h3>No bookings yet.</h3>";
      } else {
        section.innerHTML = "<h2>My Assigned Bookings</h2>";
        bookings.forEach((booking) => {
          const card = document.createElement("div");
          card.className = "booking-card";
  
          card.innerHTML = `
            <p><strong>Pickup:</strong> ${booking.pickupLocation}</p>
            <p><strong>Drop:</strong> ${booking.dropLocation}</p>
            <p><strong>Status:</strong> <span class="booking-status">${booking.status}</span></p>
            <button class="confirm-btn" data-id="${booking.id}">✅ Confirm</button>
            <button class="reject-btn" data-id="${booking.id}">❌ Reject</button>
          `;
  
          section.appendChild(card);
        });
      }
  
      // Inject booking section
      document.body.appendChild(section);
  
      // ✅ Confirm Button
      document.querySelectorAll(".confirm-btn").forEach((btn) =>
        btn.addEventListener("click", async () => {
          const bookingId = btn.dataset.id;
          const dto = {
            bookingId: bookingId,
            driverId: driverId,
            status: "CONFIRMED",
          };
          await updateBookingStatus(dto);
        })
      );
  
      // ✅ Reject Button
      document.querySelectorAll(".reject-btn").forEach((btn) =>
        btn.addEventListener("click", async () => {
          const bookingId = btn.dataset.id;
          const dto = {
            bookingId: bookingId,
            driverId: driverId,
            status: "REJECTED",
          };
          await updateBookingStatus(dto);
        })
      );
    } catch (err) {
      console.error("❌ Failed to load driver bookings", err);
      alert("Error fetching bookings");
    }
  });
  
  // ✅ Common Booking Status Update Function
  async function updateBookingStatus(dto) {
    try {
      const res = await fetch("http://localhost:8080/api/v1/bookings/update-status", {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(dto),
      });
  
      if (res.ok) {
        alert("✅ Booking status updated");
        location.reload();
      } else {
        alert("❌ Failed to update booking status");
      }
    } catch (err) {
      console.error("Error:", err);
      alert("❌ Error updating status");
    }
  }
  