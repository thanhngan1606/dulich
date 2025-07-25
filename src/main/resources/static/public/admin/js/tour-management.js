$(document).ready(function() {
    let currentPage = 0;
    let pageSize = 10;

    // Lấy danh sách tour
    function fetchTours(page = 0) {
        const params = {
            ten_tour: $('#search-ten-tour').val(),
            loai_tour: $('#search-loai-tour').val(),
            gia_tour_from: $('#search-gia-from').val(),
            gia_tour_to: $('#search-gia-to').val(),
            ngay_khoi_hanh: $('#search-ngay-khoi-hanh').val(),
            pageIndex: page,
            pageSize: pageSize
        };
        $.ajax({
            url: '/api/tour/getAllTour',
            method: 'GET',
            data: params,
            success: function(res) {
                if(res && res.data) {
                    renderTable(res.data);
                    // Không có phân trang backend trả về, có thể bổ sung nếu cần
                } else {
                    renderTable([]);
                }
            },
            error: function() {
                alert('Lỗi khi lấy dữ liệu tour!');
            }
        });
    }

    // Render bảng tour
    function renderTable(tours) {
        let html = '';
        tours.forEach(function(tour) {
            html += `<tr>
                <td>${tour.id}</td>
                <td>${tour.ten_tour}</td>
                <td>${tour.gia_tour}</td>
                <td>${tour.loai_tour}</td>
                <td>${tour.ngay_khoi_hanh ? tour.ngay_khoi_hanh.substring(0, 10) : ''}</td>
                <td>${tour.trang_thai || ''}</td>
                <td>
                    <button class="btn btn-sm btn-warning edit-btn" data-id="${tour.id}">Sửa</button>
                    <button class="btn btn-sm btn-danger delete-btn" data-id="${tour.id}">Xóa</button>
                    <a class="btn btn-sm btn-info" href="/admin/tourImage/${tour.id}">Ảnh</a>
                    <a class="btn btn-sm btn-secondary" href="/admin/tourStart/${tour.id}">Ngày KH</a>
                </td>
            </tr>`;
        });
        $('#tour-table-body').html(html);
    }

    // Sự kiện tìm kiếm
    $('#tour-search-form').on('submit', function(e) {
        e.preventDefault();
        currentPage = 0;
        fetchTours(currentPage);
    });

    // Sự kiện xóa tour
    $('#tour-table-body').on('click', '.delete-btn', function() {
        const id = $(this).data('id');
        if(confirm('Bạn có chắc muốn xóa tour này?')) {
            $.ajax({
                url: `/api/tour/delete/${id}`,
                method: 'DELETE',
                success: function(res) {
                    alert(res.message || 'Đã xóa tour!');
                    fetchTours(currentPage);
                },
                error: function() {
                    alert('Lỗi khi xóa tour!');
                }
            });
        }
    });

    // TODO: Sự kiện sửa tour (có thể mở modal, gọi API update...)

    // Load dữ liệu lần đầu
    fetchTours(currentPage);
}); 