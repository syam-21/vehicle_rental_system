document.addEventListener("DOMContentLoaded", function () {
    const driverId = localStorage.getItem("driverId");
    if (!driverId) {
      alert("Driver not logged in.");
      return;
    }
  
    fetch(`http://localhost:8080/api/v1/bookings/driver/${driverId}`)
      .then(res => res.json())
      .then(bookings => {
        const container = document.getElementById("bookingList");
        container.innerHTML = "";
  
        if (!bookings.length) {
          container.innerHTML = "<p>No bookings assigned.</p>";
          return;
        }
  
        bookings.forEach(booking => {
          const card = document.createElement("div");
          card.className = "booking-card";
  
          const pickup = booking.pickupLocation || "N/A";
          const drop = booking.dropLocation || "N/A";
  
          card.innerHTML = `
            <h3>🚗 ${booking.vehicle.brand} ${booking.vehicle.model}</h3>
            <p><strong>Pickup Date:</strong> ${booking.pickupDate}</p>
            <p><strong>Return Date:</strong> ${booking.returnDate}</p>
            <p><strong>Pickup:</strong> ${pickup}</p>
            <p><strong>Drop:</strong> ${drop}</p>
            <p><strong>Status:</strong> ${booking.status}</p>
            <p><strong>Fare:</strong> ${booking.fare} BDT</p>
            <div class="action-buttons">
              ${booking.status === "PENDING" ? `
                <button class="approve" onclick="updateBookingStatus(${booking.id}, 'CONFIRMED')">Approve</button>
                <button class="reject" onclick="updateBookingStatus(${booking.id}, 'REJECTED')">Reject</button>
              ` : ''}
            </div>
          `;
  
          container.appendChild(card);
        });
      })
      .catch(err => {
        console.error("Error loading driver bookings:", err);
      });
  });
  
  function updateBookingStatus(bookingId, status) {
    const driverId = localStorage.getItem("driverId");
  
    fetch(`http://localhost:8080/api/v1/bookings/${bookingId}/status`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ driverId, status })
    })
      .then(res => {
        if (!res.ok) throw new Error("Failed to update status");
        return res.json();
      })
      .then(() => {
        alert(`✅ Booking ${status.toLowerCase()}`);
        location.reload();
      })
      .catch(err => {
        console.error("Error:", err);
        alert("❌ Failed to update booking.");
      });
  }
  