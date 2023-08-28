import cookie from "react-cookies";

const Cart = () => {
    const carts = cookie.load("cart") || [];

    return <>
        <h1 className="text-center text-info mt-2">GIỎ HÀNG</h1>
        {Object.values(carts).map(c => <div>{c.name}</div>)}
    </>
}

export default Cart;