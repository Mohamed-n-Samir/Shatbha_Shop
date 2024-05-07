import useQueryCustom from "../../hooks/useQueryCustom";
import HashLoader from "react-spinners/HashLoader";
import { Link } from "react-router-dom";

const Categories = () => {
	const { data, isError, isFetching, isLoading, refetch } = useQueryCustom(
		["sub-categories-data"],
		"dashboard/getAllSubCategoryData",
		{
			refetchOnMount: false,
			refetchOnWindowFocus: false,
			keepPreviousData: true,
		}
	);

	if (isLoading || isFetching) {
		return (
			<div className="loading-section is-loading ">
				<HashLoader size={40} />
			</div>
		);
	}

	if (isError) {
		return (
			<div className="is-loading">
				<h2 className="text-center">
					حدث خطأ اثناء التحميل اعد تحميل الصفحه!!!
				</h2>
			</div>
		);
	}


	if (data?.data?.length === 0) {
		return (
			<div className="is-loading">
				<h2 className="text-center">!!! لا يوجد أقسام</h2>
			</div>
		);
	}

	if (data?.data?.length > 0) {
		return (
			<ul key={0} className="main-ul">
				{data?.data?.map((category) => (
					<li key={category.id} className="">
						<Link
							to={`/products?cat=${category.category?.id}`}
							onClick={() => {
								window.scrollTo(0, 0);
							}}
						>
							{category?.category?.title}
						</Link>
						<ul className="inner-ul">
							{category?.subCategory?.map((subCategory) => (
								<li key={subCategory}>
									<Link
										to={`/products?cat=${subCategory}`}
										onClick={() => {
											window.scrollTo(0, 0);
										}}
									>
										{subCategory}
									</Link>
								</li>
							))}
						</ul>
					</li>
				))}
			</ul>
		);
	}
};

export default Categories;
