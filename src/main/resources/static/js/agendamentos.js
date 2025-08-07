document.addEventListener("DOMContentLoaded", () => {
  const timeColumn = document.querySelector(".time-column");
  const calendarGrid = document.getElementById("calendar-main-grid");
  const currentPeriodDisplay = document.getElementById("current-period-display");

  let currentView = "week";
  let currentDate = new Date();

  function formatDate(date) {
    return date.toLocaleDateString("pt-BR", { day: "2-digit", month: "short", year: "numeric" });
  }

  function formatTime(date) {
    return date.toLocaleTimeString("pt-BR", { hour: "2-digit", minute: "2-digit" });
  }

  function getDayName(date) {
    return date.toLocaleDateString("pt-BR", { weekday: "short" });
  }

  function getStartOfWeek(date) {
    const day = date.getDay();
    const diff = date.getDate() - day + (day === 0 ? -6 : 1);
    const start = new Date(date);
    start.setDate(diff);
    start.setHours(0, 0, 0, 0);
    return start;
  }

  function isSameDay(d1, d2) {
    return (
      d1.getFullYear() === d2.getFullYear() &&
      d1.getMonth() === d2.getMonth() &&
      d1.getDate() === d2.getDate()
    );
  }

  function renderCalendar() {
    calendarGrid.innerHTML = "";
    timeColumn.innerHTML = "";

    const startHour = 8;
    const endHour = 18;
    const intervalMinutes = 30;

    for (let h = startHour; h < endHour; h++) {
      for (let m = 0; m < 60; m += intervalMinutes) {
        const div = document.createElement("div");
        div.classList.add("time-slot-label");
        div.textContent = `${String(h).padStart(2, "0")}:${String(m).padStart(2, "0")}`;
        timeColumn.appendChild(div);
      }
    }

    let daysToDisplay = [];
    if (currentView === "week") {
      const startWeek = getStartOfWeek(currentDate);
      const endWeek = new Date(startWeek);
      endWeek.setDate(startWeek.getDate() + 6);

      currentPeriodDisplay.textContent = `${formatDate(startWeek)} - ${formatDate(endWeek)}`;

      const corner = document.createElement("div");
      corner.classList.add("grid-header-corner");
      calendarGrid.appendChild(corner);

      for (let i = 0; i < 7; i++) {
        const day = new Date(startWeek);
        day.setDate(startWeek.getDate() + i);
        daysToDisplay.push(day);

        const header = document.createElement("div");
        header.classList.add("calendar-day-header");
        header.innerHTML = `<span class="day-name">${getDayName(day)}</span><span class="day-date">${day.getDate()}</span>`;
        if (isSameDay(day, new Date())) header.classList.add("today");
        calendarGrid.appendChild(header);
      }

      calendarGrid.style.gridTemplateColumns = `auto repeat(7, 1fr)`;
    } else {
      currentPeriodDisplay.textContent = formatDate(currentDate);

      const corner = document.createElement("div");
      corner.classList.add("grid-header-corner");
      calendarGrid.appendChild(corner);

      profissionais.forEach((p) => {
        const header = document.createElement("div");
        header.classList.add("calendar-day-header");
        header.textContent = p.nome;
        calendarGrid.appendChild(header);
      });

      calendarGrid.style.gridTemplateColumns = `auto repeat(${profissionais.length}, 1fr)`;
      daysToDisplay.push(currentDate);
    }

    const numColumns = currentView === "week" ? 7 : profissionais.length;
    const totalSlots = (endHour - startHour) * (60 / intervalMinutes);

    const gridCells = [];
    for (let i = 0; i < totalSlots; i++) {
      const time = new Date();
      time.setHours(startHour);
      time.setMinutes(i * intervalMinutes);

      for (let j = 0; j < numColumns; j++) {
        const cell = document.createElement("div");
        cell.classList.add("calendar-grid-cell");
        cell.dataset.hour = time.getHours();
        cell.dataset.minute = time.getMinutes();

        if (currentView === "week") {
          cell.dataset.date = daysToDisplay[j].toISOString().split("T")[0];
        } else {
          cell.dataset.profissionalId = profissionais[j].id;
        }

        gridCells.push(cell);
        calendarGrid.appendChild(cell);
      }
    }

    servicosAgendados.forEach((sa) => {
      const agenda = agendas.find((a) => a.id === sa.agenda.id);
      if (!agenda) return;

      const cliente = clientes.find((c) => c.id === agenda.cliente.id);
      const servico = servicos.find((s) => s.id === sa.servico.id);
      const profissional = profissionais.find((p) => p.id === sa.profissional.id);

      if (!cliente || !servico || !profissional) return;

      const start = new Date(agenda.dataHora);
      const end = new Date(start.getTime() + servico.tempoMedio * 60000);

      let isVisible = false;
      if (currentView === "week") {
        isVisible = daysToDisplay.some((d) => isSameDay(start, d));
      } else {
        isVisible = isSameDay(start, currentDate);
      }

      if (!isVisible) return;

      const card = document.createElement("div");
      card.classList.add("appointment-card");
      if (sa.status === 0) card.classList.add("cancelled");

      card.innerHTML = `
        <div class="appt-time">${formatTime(start)} - ${formatTime(end)}</div>
        <div class="appt-title">${servico.descricao}</div>
        <div class="appt-details">${cliente.nome} com ${profissional.nome}</div>
      `;

      const idx = getGridCellIndex(start, startHour, intervalMinutes, daysToDisplay, profissional, currentView);
      if (idx !== -1) {
        const target = gridCells[idx];
        const minutesIntoSlot = start.getMinutes() % intervalMinutes;
        const topOffset = (minutesIntoSlot / intervalMinutes) * 100;
        card.style.top = `${topOffset}%`;
        card.style.height = `${(servico.tempoMedio / intervalMinutes) * 100}%`;
        target.appendChild(card);
      }
    });
  }

  function getGridCellIndex(date, startHour, intervalMinutes, days, profissional, view) {
    const totalMinutes = (date.getHours() - startHour) * 60 + date.getMinutes();
    const row = Math.floor(totalMinutes / intervalMinutes);

    if (view === "week") {
      const col = days.findIndex((d) => isSameDay(date, d));
      if (col !== -1) return row * days.length + col;
    } else {
      const col = profissionais.findIndex((p) => p.id === profissional.id);
      if (col !== -1) return row * profissionais.length + col;
    }
    return -1;
  }

  document.getElementById("prev-week-btn").addEventListener("click", () => {
    currentDate.setDate(currentDate.getDate() + (currentView === "week" ? -7 : -1));
    renderCalendar();
  });

  document.getElementById("next-week-btn").addEventListener("click", () => {
    currentDate.setDate(currentDate.getDate() + (currentView === "week" ? 7 : 1));
    renderCalendar();
  });

  document.getElementById("today-btn").addEventListener("click", () => {
    currentDate = new Date();
    renderCalendar();
  });

  document.getElementById("week-view-btn").addEventListener("click", () => {
    currentView = "week";
    document.getElementById("week-view-btn").classList.add("active");
    document.getElementById("day-view-btn").classList.remove("active");
    renderCalendar();
  });

  document.getElementById("day-view-btn").addEventListener("click", () => {
    currentView = "day";
    document.getElementById("day-view-btn").classList.add("active");
    document.getElementById("week-view-btn").classList.remove("active");
    renderCalendar();
  });

  renderCalendar();
});
