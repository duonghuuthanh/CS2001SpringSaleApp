import { useContext, useEffect, useState } from "react";
import { Button, Card, Col, Row, Alert } from "react-bootstrap";
import cookie from "react-cookies";
import { useSearchParams } from "react-router-dom";
import { MyCartContext } from "../App";
import Apis, { endpoints } from "../configs/Apis";
import MySpinner from "../layout/MySpinner";

const Home = () => {
    const [cartCounter, cartDispatch] = useContext(MyCartContext);
    const [products, setProducts] = useState(null);
    const [q] = useSearchParams();

    useEffect(() => {
        let loadProducts = async () => {
            try {
                let e = endpoints['products'];

                let cateId = q.get("cateId");
                if (cateId !== null)
                    e = `${e}?cateId=${cateId}`;
                else {
                    let kw = q.get("kw");
                    if (kw !== null)
                         e = `${e}?kw=${kw}`;
                }

                let res = await Apis.get(e);
                setProducts(res.data);
            } catch (ex) {
                console.error(ex);
            }
        }

        loadProducts();

    }, [q]);

    const order = (product) => {
        cartDispatch({
            "type": "inc",
            "payload": 1
        });

        let cart = cookie.load("cart") || null;
        if (cart === null)
            cart = {}
        
        if (product.id in cart) {
            // có trong giỏ
            cart[product.id]['quantity'] += 1;
        } else {
            // không có trong giỏ
            cart[product.id] = {
                "id": product.id,
                "name": product.name,
                "quantity": 1,
                "unitPrice": product.price
            }
        }

        cookie.save("cart", cart);
        console.info(cart);
    }

    if (products === null)
        return <MySpinner />

    if (products.length === 0)
        return <Alert variant="info" className="mt-5">Không có sản phẩm nào!</Alert>

    return (
        <>
            <h1 className="text-center text-info">DANH SÁCH SẢN PHẨM</h1>
            <Row>
                {products.map(p => {
                    return <Col xs={12} md={3} className="mt-2">
                                <Card>
                                    <Card.Img variant="top" src={p.image} />
                                    <Card.Body>
                                        <Card.Title>{p.name}</Card.Title>
                                        <Card.Text>{p.price} VNĐ</Card.Text>
                                        <Button style={{marginRight: "5px"}} variant="primary">Xem chi tiết</Button>
                                        <Button variant="danger" onClick={() => order(p)}>Đặt hàng</Button>
                                    </Card.Body>
                                </Card>
                            </Col>
                })}
            </Row>
        </>
    )
}

export default Home;