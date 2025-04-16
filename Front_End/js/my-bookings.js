document.addEventListener("DOMContentLoaded", function () {
  const user = JSON.parse(localStorage.getItem("user"));
  if (!user) {
    alert("Please login first");
    return;
  }

  fetch(`http://localhost:8080/api/v1/bookings/user/${user.id}`)
    .then(res => res.json())
    .then(bookings => {
      const container = document.getElementById("myBookingsContainer");
      container.innerHTML = "";

      if (bookings.length === 0) {
        container.innerHTML = "<p>No bookings found.</p>";
        return;
      }

      bookings.forEach(booking => {
        const vehicle = booking.vehicle;
        const pickup = booking.pickupLocation || {};
        const drop = booking.dropLocation || {};

        const card = document.createElement("div");
        card.className = "booking-card";
        card.innerHTML = `
          <h3>🚗 ${vehicle.brand} ${vehicle.model}</h3>
          <p><strong>Pickup Date:</strong> ${booking.pickupDate}</p>
          <p><strong>Return Date:</strong> ${booking.returnDate}</p>
          <p><strong>Pickup:</strong> ${pickup.division || "-"} / ${pickup.district || "-"} / ${pickup.upazila || "-"}</p>
          <p><strong>Drop:</strong> ${drop.division || "-"} / ${drop.district || "-"} / ${drop.upazila || "-"}</p>
          <p><strong>Status:</strong> ${booking.status}</p>
        `;
        container.appendChild(card);
      });
    })
    .catch(err => {
      console.error("Error fetching bookings:", err);
    });
});
