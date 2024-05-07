import { Form } from "react-bootstrap";
import useQueryCustom from "../../hooks/useQueryCustom";
import HashLoader from "react-spinners/HashLoader";

const brandChoose = ({ handleChange }) => {
	const { data, isError, isFetching, isLoading, refetch } = useQueryCustom(
		["brand-table-data"],
		"dashboard/allBrands",
		{
			refetchOnMount: false,
			refetchOnWindowFocus: false,
			keepPreviousData: true,
		}
	);

	if (isLoading || isFetching) {
		return (
			<div className="is-loading">
				<HashLoader size={30} />
			</div>
		);
	}

	if (isError) {
		return (
			<div className="is-loading">
				<h1>something went wrong</h1>
			</div>
		);
	}

	if (!data) {
		return <h1>لا يوجد اقسام</h1>;
	}

	if (data?.data) {
        console.log(data?.data)
		return (
				<Form.Group controlId="brand">
					<Form.Label>الماركه</Form.Label>
					<Form.Select
						type="select"
						placeholder="اختر الماركه"
						name="brand"
						onChange={handleChange}
                        className="p-3 fs-5"
					>
						<option value="0" hidden={true}>
                            اختر الماركه
						</option>
						{data?.data?.map((brand) => {
							return (
								brand.name && (
									<option value={brand.id} key={brand.id}>
										{brand.name}
									</option>
								)
							);
						})}
					</Form.Select>
				</Form.Group>
		);
	}
};

export default brandChoose;
