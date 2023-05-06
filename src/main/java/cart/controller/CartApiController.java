package cart.controller;

import cart.auth.AuthenticationPrincipal;
import cart.dto.CartItemDto;
import cart.dto.auth.AuthInfo;
import cart.exception.AuthorizationException;
import cart.service.CartService;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartApiController {

    private final CartService cartService;

    @PostMapping("/{productId}")
    public ResponseEntity<Void> add(@PathVariable long productId, @AuthenticationPrincipal AuthInfo authInfo) {
        validateAuthInfo(authInfo);
        long cartId = cartService.add(authInfo.getEmail(), productId);
        return ResponseEntity
                .created(URI.create("/cart/" + cartId))
                .build();
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> delete(@PathVariable long cartId, @AuthenticationPrincipal AuthInfo authInfo) {
        validateAuthInfo(authInfo);
        cartService.delete(authInfo.getEmail(), cartId);
        return ResponseEntity
                .ok()
                .build();
    }

    @GetMapping("/items")
    public ResponseEntity<List<CartItemDto>> findAll(@AuthenticationPrincipal AuthInfo authInfo) {
        validateAuthInfo(authInfo);

        List<CartItemDto> allItems = cartService.findAllByMemberId(authInfo.getEmail());
        return ResponseEntity
                .ok()
                .body(allItems);
    }

    public void validateAuthInfo(AuthInfo authInfo) {
        if (authInfo == null) {
            throw new AuthorizationException("사용자 인증이 필요합니다.");
        }
    }
}
