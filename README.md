- Một số lưu ý khi code clean :
- Trong 1 số trường hợp nếu use case không chứa các logic khác so với repository impl (use case không chưa thêm các logic nghiệp vụ mà chỉ đơn thuần là gọi hàm từ repository impl) thì có thể bỏ usecase, gọi trực tiếp phương thức từ repository impl trong viewmodel
- Không nên truyền view model vào các màn hình, chỉ nên truyền các kết quả của hàm và các funtion thông qua 1 sealed class
  
