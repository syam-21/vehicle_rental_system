window.addEventListener("DOMContentLoaded", () => {
  fetch("http://localhost:8080/api/v1/bookings")
    .then(res => {
      if (!res.ok) throw new Error("Failed to fetch bookings");
      return res.json();
    })
    .then(bookings => {
      const tbody = document.querySelector("#bookingsTable tbody");
      tbody.innerHTML = "";

      bookings.forEach(booking => {
        const row = document.createElement("tr");
        row.innerHTML = `
          <td>${booking.id}</td>
          <td>${booking.user?.name || "N/A"}</td>
          <td>${booking.vehicle?.brand || "N/A"} ${booking.vehicle?.model || ""}</td>
          <td>${booking.pickupDate}</td>
          <td>${booking.returnDate}</td>
          <td>
            <button onclick="cancelBooking(${booking.id})"
              style="background:red;color:white;border:none;padding:6px 10px;border-radius:6px;cursor:pointer;">
              Cancel
            </button>
          </td>
        `;
        tbody.appendChild(row);
      });
    })
    .catch(err => {
      console.error("Booking load failed:", err);
      const tbody = document.querySelector("#bookingsTable tbody");
      tbody.innerHTML = `<tr><td colspan="6">❌ Error loading bookings</td></tr>`;
    });
});

// ✅ DELETE FUNCTION TO CANCEL A BOOKING
function cancelBooking(id) {
  if (!confirm("Are you sure you want to cancel this booking?")) return;

  fetch(`http://localhost:8080/api/v1/bookings/${id}`, {
    method: "DELETE"
  })
    .then(res => {
      if (!res.ok) throw new Error("Failed to delete booking");
      alert("✅ Booking cancelled");
      location.reload(); // Reload the page to refresh table
    })
    .catch(err => {
      console.error(err);
      alert("❌ Error cancelling booking");
    });
}
