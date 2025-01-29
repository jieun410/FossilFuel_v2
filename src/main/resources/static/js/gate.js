let guestbookEntries = [];
let currentPage = 1;
const entriesPerPage = 6;

// 방명록 추가
function addGuestbook() {
    const input = document.getElementById('guestbookInput');
    if (input.value.trim() !== '') {
        guestbookEntries.push(input.value);
        input.value = '';
        renderGuestbook();
    }
}

// 방명록 렌더링
function renderGuestbook() {
    const container = document.getElementById('guestbookContainer');
    container.innerHTML = '';

    const start = (currentPage - 1) * entriesPerPage;
    const end = start + entriesPerPage;
    const pageEntries = guestbookEntries.slice(start, end);

    pageEntries.forEach(entry => {
        const div = document.createElement('div');
        div.className = 'guestbook-entry';
        div.textContent = entry;
        container.appendChild(div);
    });

    document.getElementById('pageNumber').textContent = currentPage;
}

// 이전 페이지
function prevPage() {
    if (currentPage > 1) {
        currentPage--;
        renderGuestbook();
    }
}

// 다음 페이지
function nextPage() {
    if (currentPage * entriesPerPage < guestbookEntries.length) {
        currentPage++;
        renderGuestbook();
    }
}