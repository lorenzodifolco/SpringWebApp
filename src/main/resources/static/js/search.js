window.onload = () => {
    const context = document.querySelector('base').getAttribute('href');
    const ticketList = document.getElementById("tickets-container");
    const ticketListItemsBackup = ticketList.innerHTML;

    document.getElementById("search-input").addEventListener("keyup", (e) => {
        if (e.target.value.length >= 3) {
            const searchValue = e.target.value.toLowerCase();
            fetch(`${context}ticket/search?q=${searchValue}`, {
                method: "GET",
            }).then((response) => {
                return response.json();
            }).then((data) => {
                ticketList.innerHTML = "";
                data.forEach((ticket) => {

                    ticketList.innerHTML += `
                        <div id="ticket" class="col-md-4 gy-3">
                            <div class="card h-100">
                                    <div class="card-body">
                                    <h5 class="card-title">${ticket.title}</h5>
                                    <h6 class="card-subtitle mb-2 text-muted">
                                        <span>${ticket.type}</span>
                                        <span class="float-end">${ticket.state}</span>
                                    </h6>
                                    <p class="card-text text-truncate">${ticket.description}</p>
                                    <div class="btn-group">
                                        <a class="btn btn-sm btn-outline-secondary" href="/ticket/${ticket.id}">View</a>
                                        <a class="btn btn-sm btn-outline-secondary" href="/ticket/${ticket.id}/edit">Edit</a>
                                        <a class="btn btn-sm btn-outline-secondary" href="/">Set time spent</a>
                                    </div>
                                </div>
                                <div class="card-footer">
                                    <small class="text-muted">
                                        <span> ${ticket.author.username}</span>
                                        <time class="float-end">${ticket.date}</time>
                                    </small>
                                </div>
                            </div>
                        </div>
                    `;
                });
            });
        } else {
            ticketList.innerHTML = ticketListItemsBackup;
        }
    });
};