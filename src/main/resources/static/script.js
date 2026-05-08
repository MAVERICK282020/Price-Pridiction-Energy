let chart;

async function loadDashboard() {
    const today = await fetch('/api/energy/today').then(r => r.json());
    document.getElementById('todayKwh').innerText = Number(today.todayKwh).toFixed(2) + ' kWh';
    document.getElementById('bill').innerText = '₹' + Number(today.estimatedBill).toFixed(2);

    const prediction = await fetch('/api/energy/predict?deviceId=HOME-001').then(r => r.json());
    document.getElementById('prediction').innerText = prediction.predictedNextKwh + ' kWh';

    const readings = await fetch('/api/energy/readings?deviceId=HOME-001').then(r => r.json());
    renderChart(readings);
}

function renderChart(readings) {
    const labels = readings.map(r => new Date(r.readingTime).toLocaleTimeString());
    const data = readings.map(r => r.energyKwh);

    const ctx = document.getElementById('energyChart');
    if (chart) chart.destroy();
    chart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: labels,
            datasets: [{
                label: 'Energy kWh',
                data: data,
                tension: 0.3
            }]
        },
        options: {
            responsive: true,
            scales: { y: { beginAtZero: true } }
        }
    });
}

async function addReading() {
    const reading = {
        deviceId: document.getElementById('deviceId').value,
        voltage: Number(document.getElementById('voltage').value),
        currentAmp: Number(document.getElementById('currentAmp').value),
        powerWatt: Number(document.getElementById('powerWatt').value),
        energyKwh: Number(document.getElementById('energyKwh').value)
    };

    const response = await fetch('/api/energy/reading', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(reading)
    });

    const saved = await response.json();
    document.getElementById('saveStatus').innerText = saved.anomaly
        ? 'Saved. Alert: anomaly detected!'
        : 'Saved successfully.';
    loadDashboard();
}

async function askBot() {
    const input = document.getElementById('chatInput');
    const message = input.value.trim();
    if (!message) return;

    addMessage('You', message, 'user');
    const response = await fetch('/api/chatbot/ask', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ message })
    }).then(r => r.json());

    addMessage('Bot', response.reply, 'bot');
    input.value = '';
}

function addMessage(sender, text, cls) {
    const chatBox = document.getElementById('chatBox');
    const div = document.createElement('div');
    div.className = 'message ' + cls;
    div.innerHTML = `<span>${sender}:</span> ${text}`;
    chatBox.appendChild(div);
    chatBox.scrollTop = chatBox.scrollHeight;
}

loadDashboard();
setInterval(loadDashboard, 10000);
