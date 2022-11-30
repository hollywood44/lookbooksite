package com.lbs.lookbooksite.service;

import com.lbs.lookbooksite.configs.FileManager;
import com.lbs.lookbooksite.domain.Product;
import com.lbs.lookbooksite.domain.Product_Image;
import com.lbs.lookbooksite.dto.product.ProductDto;
import com.lbs.lookbooksite.repository.ProductRepository;
import com.lbs.lookbooksite.repository.Product_ImageRepository;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    //<editor-fold desc="기본 설정 메소드, 멤버">

    // 실제 파일 업로드 담당하는 컴포넌트
    private final FileManager fileManager;

    // 프로퍼티에 설정해놓은 위치 사용
    @Value("${file.upload.productImg}")
    private String productFilePath;

    private final ProductRepository repository;
    private final Product_ImageRepository imageRepository;
    public String makeFolder() {
        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        String folderPath = str.replace("/", File.separator);

        File uploadPathFolder = new File(productFilePath, folderPath);

        if (!uploadPathFolder.exists()) {
            uploadPathFolder.mkdirs();
        }

        return folderPath;
    }

    //</editor-fold>

    //<editor-fold desc="업로드관련">

    // 이미지 포함 업로드
    @Override @Transactional
    public String uploadProductWithImg(ProductDto dto) {

        // 이미지파일들 빼고 엔티티에 들어가 있는 상태
        Product uploadPD = dtoToEntity(dto);

        try {
            for (MultipartFile img : dto.getGetImages()) {
                // 실제 파일명
                String originName = img.getOriginalFilename();
                // 랜덤 파일명 생성
                String uuid = UUID.randomUUID().toString();
                // 저장될 파일명(랜덤파일명_실제파일명)
                String savedName = uuid + "_" + originName;
                // 저장할 위치경로
                Path savePath = Paths.get(productFilePath + File.separator + makeFolder() + File.separator + savedName);

                // 파일 업로드
                fileManager.fileUpload(img, savePath);

                // 썸네일 생성 (나중에 썸네일 가지고 오고 싶을때
                // lastindexof "_" 위치를 "_s_"로 replace해서 사용)
                String thumbnailSaveName = productFilePath + File.separator + makeFolder() + File.separator + uuid + "_s_" + originName;
                File thumbnailFile = new File(thumbnailSaveName);
                Thumbnailator.createThumbnail(savePath.toFile(), thumbnailFile, 200, 200);

                // /boardImg/**로 사용하기 쉽게  (/boardImg/년/월/일/파일명)으로 저장
                int index = savePath.toString().lastIndexOf("/productImg");
                String storedPath = savePath.toString().substring(index);

                Product_Image product_image = Product_Image.builder()
                        .storedName(savedName)
                        .originName(originName)
                        .storedPath(storedPath)
                        .build();
                // 영속성 전이
                uploadPD.addImgs(product_image);
            }
            //DB에 저장
            return repository.save(uploadPD).getProductId();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // 이미지 포함안하는 업로드
    @Override
    public String uploadProductWithOutImg(ProductDto dto) {
        Product product = Product.builder()
                .productId(dto.getProductId())
                .productName(dto.getProductName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .build();

        return repository.save(product).getProductId();
    }

    //</editor-fold>

    //<editor-fold desc="가져오기 관련">

    // 상세 상품 보기
    @Override
    public List<ProductDto> getAllProductList() {
        Function<Product, ProductDto> fn = (entity->(entityToDTO(entity)));
        List<ProductDto> allProduct = null;
        List<Product> entityList = repository.findAll();

        if (!entityList.isEmpty()) {
            return allProduct = entityList.stream().map(fn).collect(Collectors.toList());
        } else {
            return allProduct;
        }
    }


    // 상품 정보 가져오기
    @Override
    public ProductDto getProduct(String productId) {
        Optional<Product> entity = repository.findById(productId);
        if (entity.isPresent()) {
            ProductDto product = entityToDTO(entity.get());
            return product;
        } else {
            return null;
        }
    }

    // 상품 수정
    @Override
    @Transactional
    public String modifyProduct(ProductDto dto,int checkStatus) {
        Product modiProduct = repository.findById(dto.getProductId()).get();

        // 이미지빼고 바꾼상태
        modiProduct.modifyProduct(dto);

        if (checkStatus == 1) {
            modiProduct.deleteImgs();
            try {
                for (MultipartFile img : dto.getGetImages()) {
                    // 실제 파일명
                    String originName = img.getOriginalFilename();
                    // 랜덤 파일명 생성
                    String uuid = UUID.randomUUID().toString();
                    // 저장될 파일명(랜덤파일명_실제파일명)
                    String savedName = uuid + "_" + originName;
                    // 저장할 위치경로
                    Path savePath = Paths.get(productFilePath + File.separator + makeFolder() + File.separator + savedName);

                    // 파일 업로드
                    fileManager.fileUpload(img, savePath);

                    // /boardImg/**로 사용하기 쉽게  (/boardImg/년/월/일/파일명)으로 저장
                    int index = savePath.toString().lastIndexOf("/productImg");
                    String storedPath = savePath.toString().substring(index);

                    Product_Image product_image = Product_Image.builder()
                            .storedName(savedName)
                            .originName(originName)
                            .storedPath(storedPath)
                            .build();
                    // 영속성 전이
                    modiProduct.addImgs(product_image);
                }
                //DB에 저장
                return repository.save(modiProduct).getProductId();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
            return repository.save(modiProduct).getProductId();
    }


    //</editor-fold>


    @Override
    @Transactional
    public String deleteProduct(String productId) {
        repository.deleteById(productId);
        return productId;
    }
}
